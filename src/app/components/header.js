'use strict';

var React = require('react');
var Link = require('react-router').Link;

module.exports = React.createClass({

  render: function() {
    return (
      <header>
        <div className="container">
          <div className="nav row">
            <a href="/"><h3 className="logo six columns">Hotelr</h3></a>
            <div className="logged six columns">
              <a href="/login">Login</a>
            </div>
          </div>
        </div>
      </header>
    );
  }

});
