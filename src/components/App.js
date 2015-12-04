import React from 'react';
import { Link }  from 'react-router';

import Messages from './Messages';
import Header from './Header-home';
import Footer from './Footer';

export default React.createClass({
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
