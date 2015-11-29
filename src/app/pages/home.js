'use strict';

var React = require('react');

//Components
var Messages = require('../components/messages');
var Header = require('../components/headerhome');
var Footer = require('../components/footer');

module.exports = React.createClass({
  render: function() {
    return (
      <div>
        <Messages/>
        <Header/>
        <Footer/>
      </div>
    );
  }
});

