from flask import Flask, request, jsonify, render_template
import pickle
import numpy as np

app = Flask(__name__)
model = pickle.load(open('model.pkl','rb'))

@app.route('/')
def home():
    return 'Welcome to Reality'
  
@app.route("/predict", methods = ["GET"])
def predict():
    path = [float(x) for x in request.form.values()]
    features = [np.array(path)]
    prediction = model.predict(features)
    return jsonify({'Rekomendasi': prediction})

if __name__=="__main__":
    app.run(debug=True)
