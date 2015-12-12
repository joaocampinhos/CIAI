import React from 'react';
import { Link }  from 'react-router';

import Header from './Header';
import Footer from './Footer';

export default React.createClass({
  getInitialState() {
    let { query } = this.props.location
    return {
      arrival: query.arrival || this.date('today'),
      departure: query.departure || this.date('tomorrow'),
      roomtype: query.roomtype || '',
      name: query.name || undefined,
      hotels: [],
      roomtypes: []
    };
  },
  date: function(date) {
    if (date === 'today') {
      return new Date().toJSON().slice(0,10);
    }
    if (date === 'tomorrow') {
     return new Date(new Date().getTime() + 24 * 60 * 60 * 1000).toJSON().slice(0,10);
    }
  },
  update: function(e) {
    this.setState({ roomtype: this.refs.room.options[this.refs.room.selectedIndex].value });
  },
  componentDidMount() {
    var that = this;
    fetch('http://localhost:8080/hotels')
    .then(function(response) {
      return response.json()
    }).then(function(json) {
      that.setState({hotels: json.hotels});
    }).catch(function(ex) {})
    fetch('http://localhost:8080/roomtypes')
    .then(function(response) {
      return response.json()
    }).then(function(json) {
     that.setState({roomtypes: json.roomtypes});
    }).catch(function(ex) {})
  },
  render: function() {
    var hots = [];
    var that = this;
    const roomtype = this.state.roomtypes.map(function(room) {
      return (
        <option value={room.id}>{room.name}</option>
      );
    });
    var hotels = '';
    if (this.state.hotels.length > 0) {
      var hots = this.state.hotels.filter(function(el) {
        if (that.state.name) {
          if (el.name.toUpperCase().indexOf(that.state.name.toUpperCase()) !== -1)
            return true;
          else
            return false;
        }
        return true;
      });
      var hotels = hots.map(function (hotel) {
        return (
          <div className="media hotel-list">
            <img src="https://placehold.it/150x150" alt="" className="media__img"/>
            <div className="media__body">
              <div className="row">
                <div className="nine columns">
                  <h5>
                    <Link to={"/hotels/"+hotel.id} className="a-none">{hotel.name}</Link> 
                    <span className={'star-' + hotel.rating}></span>
                  </h5>
                  <p className="clearmargin"> Address: <span>{hotel.address}</span></p>
                  <p className="clearmargin"> Category: <span>{hotel.category}</span></p>
                </div>
                <div className="three columns">
                  <div className="right">
                    {hotel.comments.length} comments
                  </div>
                </div>
              </div>
              <div className="row bottomrow">
                <div className="eight columns">
                </div>
                <div className="right four columns">
                  <Link to={"/hotels/"+hotel.id+'?arrival='+that.state.arrival+'&departure='+that.state.departure+'&roomtype='+that.state.roomtype} className="gohotel clearmargin button button-full button-primary">Book now</Link>
                </div>
              </div>
            </div>
          </div>
        );
      });
    }
    return (
      <div>
        <Header/>
        <div >
          <form className="clear" method="GET">
            <div className="container">
              <br/>
              <div className="row">
                <div className="four columns">
                  <label>Dates</label>
                </div>
                <div className="four columns">
                  <div className="isle-1-h">
                    <input name="arrival" className="clear" defaultValue={this.state.arrival} type="date"/>
                  </div>
                </div>
                <div className="four columns">
                  <div className="isle-1-h">
                    <input name="departure" className="clear" defaultValue={this.state.departure} type="date"/>
                  </div>
                </div>
              </div>
              <br/>
              <hr className="clear"/>
              <br/>
            </div>
            <div className="container">
              <div className="row">
                <div className="four columns">
                  <label>Hotel Name</label>
                </div>
                <div className="eight columns">
                  <div className="isle-1-h">
                    <input name="name" defaultValue={this.state.name} type="text"></input>
                  </div>
                </div>
              </div>
              <br/>
              <hr className="clear"/>
              <br/>
            </div>
            <div className="container">
              <div className="row">
                <div className="four columns">
                  <label>Room Type</label>
                </div>
                <div className="eight columns">
                  <div className="isle-1-h">
                    <select ref="room" onChange={this.update} name="roomtype" value={this.state.roomtype} className="clear">
                      <option value=''>Room Type</option>
                      {roomtype}
                    </select>
                  </div>
                </div>
              </div>
              <br/>
            </div>
            <div className="secondary">
              <div className="container isle-1-v">
                <button type="submit">Filter</button>
                <span className="right" style={{lineHeight: '48px'}}><b><span>{hots.length}</span> </b>Hotels Found</span>
              </div>
            </div>
          </form>
          <div className="secondary">
            <div className="container">
              {hotels}
            </div>
          </div>
        </div>
        <Footer/>
      </div>
    );
  }
});