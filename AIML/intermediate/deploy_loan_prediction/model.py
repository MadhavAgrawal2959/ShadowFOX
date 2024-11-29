# Streamlit app corrected
import streamlit as st
import numpy as np
import pandas as pd
import pickle

# Load the trained model and imputer
with open('AIML/intermediate/deploy_loan_prediction/logistic_model.pkl', 'rb') as file:
    model = pickle.load(file)

with open('AIML/intermediate/deploy_loan_prediction/imputer.pkl', 'rb') as file:
    imputer = pickle.load(file)

# Streamlit app title
st.title("Loan Prediction Application")

# Create input fields for user input
gender = st.selectbox("Gender", ["Male", "Female"])
married = st.selectbox("Married", ["Yes", "No"])
dependents = st.selectbox("Number of Dependents", [0, 1, 2, 3])  # Optional: Drop if unused
education = st.selectbox("Education", ["Graduate", "Not Graduate"])
self_employed = st.selectbox("Self Employed", ["Yes", "No"])
applicant_income = st.number_input("Applicant Income", min_value=0.0, step=1.0)
coapplicant_income = st.number_input("Coapplicant Income", min_value=0.0, step=1.0)
loan_amount = st.number_input("Loan Amount", min_value=0.0, step=1.0)
loan_amount_term = st.number_input("Loan Amount Term (in days)", min_value=0, step=1)
credit_history = st.selectbox("Credit History", [1, 0])
property_area = st.selectbox("Property Area", ["Urban", "Semiurban", "Rural"])

# Convert input to match the dataset's encoding
input_data = {
    "Gender": 1 if gender == "Male" else 0,
    "Married": 1 if married == "Yes" else 0,
    "Education": 1 if education == "Graduate" else 0,
    "Self_Employed": 1 if self_employed == "Yes" else 0,
    "ApplicantIncome": applicant_income,
    "CoapplicantIncome": coapplicant_income,
    "LoanAmount": loan_amount,
    "Loan_Amount_Term": loan_amount_term,
    "Credit_History": credit_history,
    "Property_Area": {"Urban": 2, "Semiurban": 1, "Rural": 0}[property_area],
}

# Drop unused feature (if applicable)
# input_data.pop("Dependents")  # Remove if unused during training

# Convert input to DataFrame and reorder columns
input_df = pd.DataFrame([input_data])

# Ensure column order matches training
feature_order = [
    "Gender", 
    "Married", 
    "Education", 
    "Self_Employed", 
    "ApplicantIncome", 
    "CoapplicantIncome", 
    "LoanAmount", 
    "Loan_Amount_Term", 
    "Credit_History", 
    "Property_Area"
]
input_df = input_df[feature_order]

# Preprocess input data (impute missing values)
try:
    input_array = imputer.transform(input_df)
except ValueError as e:
    st.error(f"Input preprocessing error: {e}")
    st.stop()

# Predict button
if st.button("Predict Loan Status"):
    prediction = model.predict(input_array)
    loan_status = "Y" if prediction[0] == 1 else "N"
    st.write(f"### Loan Status: **{loan_status}**")
