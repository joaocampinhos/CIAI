import React from 'react';
import { Link }  from 'react-router';

export default React.createClass({
  getDefaultProps: function () {
    return {
      message: {
        type: '',
        value: ''
      }
    }
  },
  getInitialState: function () {
    return {
      isVisible: false
    };
  },
  show: function () {
    this.setState({isVisible: true});
  },
  hide: function () {
    this.setState({isVisible: false});
  },
  render: function() {
    let hide = 'hidden';
    if (this.state.isVisible) {
      hide = '';
    }
    return (
      <div>
        <div className={'message-'+this.props.message.type+' '+hide}>
          <div className="container message isle-1-v">
            <span>{this.props.message.value}</span>
            <span onClick={this.hide} className="message-close right">✖</span>
          </div>
        </div>
      </div>
    );
  }

});
