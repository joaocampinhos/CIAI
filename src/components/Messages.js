import React from 'react';
import { Link }  from 'react-router';

export default React.createClass({
  getInitialState: function() {
    if (! this.props.message) return {hidden: true};
    return {
      hidden: false,
      type: this.props.message.type,
      text: this.props.message.text
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
            <span>{this.state.text}</span>
            <span onClick={this.handleClick} className="message-close right">✖</span>
          </div>
        </div>
      </div>
    );
  }

});
