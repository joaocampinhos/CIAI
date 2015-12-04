import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Route } from 'react-router';
import App from './components/App';

// useless
import PoweredBy from './components/Powered-by';
import About from './components/About';

import Home from './components/App';
import Hotels from './components/Hotels';
import Hotel from './components/Hotel';
import Login from './components/Login';

window.React = React;

ReactDOM.render(
  <Router>
    <Route path="/" component={Home}/>
    <Route path="login" component={Login}/>
    <Route path="hotels" component={Hotels}/>
    <Route path="/hotels/:hotelid" component={Hotel}/>
  </Router>
  , document.getElementById('content')
);
