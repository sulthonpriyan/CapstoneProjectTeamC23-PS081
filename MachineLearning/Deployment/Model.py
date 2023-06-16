import pandas as pd
from typing import Dict, Text
import csv
import numpy as np
import pandas as pd
import tensorflow as tf
import tensorflow_recommenders as tfrs
from sklearn.metrics import pairwise_distances
from sklearn.preprocessing import StandardScaler
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
import pickle

ratings = pd.read_csv('E:/Perkuliahan Duniawi/MBKM Bangkit 2023/Capstone/NITANI_Deploy/rating.csv')
produks = pd.read_csv('E:/Perkuliahan Duniawi/MBKM Bangkit 2023/Capstone/NITANI_Deploy/produk.csv')

# join the ratings with the produks
ratings = pd.merge(ratings, produks, on='produkid')

# keep only produkws with a rating greater than 3
ratings = ratings[ratings.rating > 3]

# keep only the user id and the produkname columns
ratings = ratings[['produkname', 'userid']].reset_index(drop=True)

ratings_df = ratings
produks_df = produks

ratings_df.rename(columns={'produkname': 'produk_title'}, inplace=True)
produks_df.rename(columns={'produkname': 'produk_title'}, inplace=True)

# convert them to tf datasets
ratings = tf.data.Dataset.from_tensor_slices(dict(ratings_df))
produks = tf.data.Dataset.from_tensor_slices(dict(produks_df))

# Select the basic features.
ratings = ratings.map(lambda x: {
    "produk_title": x["produk_title"],
    "userid": x["userid"]
})
produks = produks.map(lambda x: x["produk_title"])

userids_vocabulary = tf.keras.layers.IntegerLookup(mask_token=None)
userids_vocabulary.adapt(ratings.map(lambda x: x["userid"]))

produk_titles_vocabulary = tf.keras.layers.StringLookup(mask_token=None)
produk_titles_vocabulary.adapt(produks)


class produkLensModel(tfrs.Model):
    def __init__(self, user_model: tf.keras.Model, produk_model: tf.keras.Model, task: tfrs.tasks.Retrieval):
        super().__init__()

        # Set up user and produk representations.
        self.user_model = user_model
        self.produk_model = produk_model

        # Set up a retrieval task.
        self.task = task

    def compute_loss(self, features: Dict[Text, tf.Tensor], training=False) -> tf.Tensor:
        user_embeddings = self.user_model(features["userid"])
        produk_embeddings = self.produk_model(features["produk_title"])

        return self.task(user_embeddings, produk_embeddings)


# Define user and produk models.
user_model = tf.keras.Sequential([
    userids_vocabulary,
    tf.keras.layers.Embedding(userids_vocabulary.vocabulary_size(), 64)
])
produk_model = tf.keras.Sequential([
    produk_titles_vocabulary,
    tf.keras.layers.Embedding(produk_titles_vocabulary.vocabulary_size(), 64)
])

# Define your objectives.
task = tfrs.tasks.Retrieval(metrics=tfrs.metrics.FactorizedTopK(produks.batch(128).map(produk_model)))

# Create a retrieval model.
model = produkLensModel(user_model, produk_model, task)
model.compile(optimizer=tf.keras.optimizers.Adagrad(0.5), run_eagerly=True)

# Train for 6 epochs.
model.fit(ratings.batch(4096), epochs=6)

# Use brute-force search to set up retrieval using the trained representations.
index = tfrs.layers.factorized_top_k.BruteForce(model.user_model)
index.index_from_dataset(produks.batch(100).map(lambda title: (title, model.produk_model(title))))

# Get some recommendations.
_, titles = index(np.array([8]))
print(f"Top 10 recommendations for user 8: {titles[0, :3]}")

# get the users embeddings
users_embdeddings = user_model.weights[1].numpy()

# get the mapping of the user ids from the vocabulary
users_idx_name = userids_vocabulary.get_vocabulary()

# print the shape
users_embdeddings.shape

# get the produks embeddings
produks_embdeddings = produk_model.weights[1].numpy()

# get the mapping of the produk tiles from the vocabulary
produkidx_name = produk_titles_vocabulary.get_vocabulary()

# print the shape of the produks embeddings
produks_embdeddings.shape

# get the cosine similarity of all pairs
produks_similarity = 1 - pairwise_distances(produks_embdeddings, metric='cosine')

# get the upper triangle in order to take the unique pairs
produks_similarity = np.triu(produks_similarity)
produk_A = np.take(produkidx_name, np.where((produks_similarity > 0.8))[0])
produk_B = np.take(produkidx_name, np.where((produks_similarity > 0.8))[1])

similar_produks = pd.DataFrame({'produk_A': produk_A, 'produk_B': produk_B})
similar_produks.head(100)

# get the product of users and produks embeddings
product_matrix = np.matmul(users_embdeddings, np.transpose(produks_embdeddings))

# get the shape of the product matrix
product_matrix.shape

# score of produks for user 8
user_8_produks = product_matrix[users_idx_name.index(8), :]

# return the top 10 produks
np.take(produkidx_name, user_8_produks.argsort()[::-1])[0:10]

seen_produks = ratings_df.query('userid==8')['produk_title'].values

np.setdiff1d(np.take(produkidx_name, user_8_produks.argsort()[::-1]), seen_produks, assume_unique=True)[0:10]
import tempfile
import os
# Export the query model.
with tempfile.TemporaryDirectory() as tmp:
    path = os.path.join(tmp, "model")

    # Save the index.
    tf.saved_model.save(index, path)

    # Load it back; can also be done in TensorFlow Serving.
    loaded = tf.saved_model.load(path)

    # Pass a user id in, get top predicted produknames back.
    scores, titles = loaded([46])
    print(f"Recommendations: {titles[0][:3]}")

# Save the model as a pickle file
pickle.dump(index, open("model.pkl", "wb"))
