import React from 'react';
import { Link }  from 'react-router';

export default React.createClass({
  render: function() {
    return (
      <footer>
        <img src="/src/images/logo.png" className="info"></img>
        <div className="about">
          <a href="/about">About us</a> &middot; <a href="/about">Site map</a> &middot; <a href="/about">Contact us</a>
        </div>
      </footer>
    );
  }
});
