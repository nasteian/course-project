import './App.css';
import React from 'react';
import RegisterPage_Pevneva from './pages/RegisterPage_Pevneva';
import RegisterSuccessPage_Pevneva from './pages/RegisterSuccessPage_Pevneva';
import LoginPage_Pevneva from './pages/LoginPage_Pevneva';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import PageUrls_Pevneva from "./settings/PageUrls_Pevneva";
import HomePage_Pevneva from './pages/HomePage_Pevneva';

function App() {
  return (
    <Router>
      <Routes>
        <Route path={PageUrls_Pevneva.REGISTER} element={<RegisterPage_Pevneva />} />
        <Route path={PageUrls_Pevneva.REGISTER_SUCCESS} element={<RegisterSuccessPage_Pevneva />} />
        <Route path={PageUrls_Pevneva.LOGIN} element={<LoginPage_Pevneva />} />
        <Route path={PageUrls_Pevneva.HOME} element={<HomePage_Pevneva />} />
      </Routes>
    </Router>
  );
}

export default App;
