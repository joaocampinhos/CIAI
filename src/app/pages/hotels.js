'use strict';

var React = require('react');
var Router = require('react-router').Router;
var Route = require('react-router').Route;
var Link = require('react-router').Link;

//Components
var Messages = require('../components/messages');
var Header = require('../components/header');
var Footer = require('../components/footer');

module.exports = React.createClass({
  render: function() {
    return (
      <div>
        <Messages/>
        <Header/>
        <li><Link to={`/hotels/1`}>hotel</Link></li>
        <Footer/>
      </div>
    )
  }
});
