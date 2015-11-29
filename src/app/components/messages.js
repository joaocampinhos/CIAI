'use strict';

var React = require('react');
var Link = require('react-router').Link;

module.exports = React.createClass({

  getInitialState: function() {
    {/* so se houver mensagem */}
    return {
      hidden: false,
      type: 'error'
    };
  },

  handleClick: function(event) {
    this.setState({hidden: true});
  },

  render: function() {
    let classes = this.state.hidden ? 'hidden' : '';
    return (
      <div>
        <div className={'message-'+this.state.type+' '+classes}>
          <div className="container message isle-1-v">
            <span>Texto</span>
            <span onClick={this.handleClick} className="message-close right">✖</span>
          </div>
        </div>
      </div>
    );
  }

});
