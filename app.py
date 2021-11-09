from flask import Flask,request,jsonify
import pickle
import numpy as np

model = pickle.load(open('titanic.pkl','rb'))

app = Flask(__name__)

@app.route('/')
def home():
    return "Hello World"

@app.route('/predict', methods=['POST'])
def predict():
    Pclass = request.form.get('Pclass')
    Age = request.form.get('Age')
    SibSp = request.form.get('SibSp')
    Parch = request.form.get('Parch')
    Fare = request.form.get('Fare')
    male = request.form.get('male')
    Q = request.form.get('Q')
    S = request.form.get('S')
    
    # result = {'sex':sex,'Age':Age,'Pclass':Passenger_Class,'Port_S':Port_S,'Port_Q':Port_Q,'Fare':Fare,'Parch':Parch,'SibSp':SibSp}
  
    input_query = np.array([[Pclass, Age, SibSp, Parch, Fare, male, Q, S]])

    result = model.predict(input_query)[0]

    return jsonify({'Survived':str(result)})

if __name__ == '__main__':
    app.run(debug = True)