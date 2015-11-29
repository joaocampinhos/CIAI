'use strict';

var React = require('react');
var Link = require('react-router').Link;

module.exports = React.createClass({

  render: function() {
    return (
      <footer>
        <img src="assets/images/logo.png" className="info"></img>
        <div className="about">
          <a href="/about">About us</a> &middot; <a href="/about">Site map</a> &middot; <a href="/about">Contact us</a>
        </div>
      </footer>
    );
  }

});
