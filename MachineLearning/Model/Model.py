!pip install -q tensorflow-recommenders
from typing import Dict, Text

import numpy as np
import pandas as pd
import tensorflow as tf
import tensorflow_recommenders as tfrs

# read the csv files as pandas data frames
ratings_df = ratings#pd.read_csv('ratings.csv')
produks_df = produks#pd.read_csv('produks.csv')


ratings_df.rename(columns = {'produkname': 'produk_title'}, inplace=True)
produks_df.rename(columns = {'produkname': 'produk_title'},  inplace=True)
# convert them to tf datasets
ratings = tf.data.Dataset.from_tensor_slices(dict(ratings_df))
produks = tf.data.Dataset.from_tensor_slices(dict(produks_df))
# get the first rows of the produks dataset
for m in produks.take(5):
  print(m)
# get the first rows of the ratings dataset
for r in ratings.take(4):
  print(r)
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
  # We derive from a custom base class to help reduce boilerplate. Under the hood,
  # these are still plain Keras Models.

  def __init__(
      self,
      user_model: tf.keras.Model,
      produk_model: tf.keras.Model,
      task: tfrs.tasks.Retrieval):
    super().__init__()

    # Set up user and produk representations.
    self.user_model = user_model
    self.produk_model = produk_model

    # Set up a retrieval task.
    self.task = task

  def compute_loss(self, features: Dict[Text, tf.Tensor], training=False) -> tf.Tensor:
    # Define how the loss is computed.

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
task = tfrs.tasks.Retrieval(metrics=tfrs.metrics.FactorizedTopK(
    produks.batch(128).map(produk_model)
  )
)
