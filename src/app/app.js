'use strict';

var React = require('react');
var Router = require('react-router').Router;
var Route = require('react-router').Route;
var Link = require('react-router').Link;
var createBrowserHistory = require('history/lib/createBrowserHistory')

//Pages
var home = require('./pages/home');
var login = require('./pages/login');
var hotels = require('./pages/hotels');
var hotel = require('./pages/hotel');

React.render((
  <Router history={createBrowserHistory()}>
    <Route path="/" component={home}/>
    <Route path="hotels" component={hotels}/>
    <Route path="/hotels/:hotelid" component={hotel}/>
    <Route path="login" component={login}/>
  </Router>
), document.body)
