import React from 'react';
import auth from '../services/auth';
import { History, Link }  from 'react-router';

import Header from './Header';
import Footer from './Footer';

export default React.createClass({

  mixins: [ History ],

  getInitialState() {
    let { query } = this.props.location
    return {
      price: 0,
      hotel: {manager: {}, rooms: []},
      arrival: query.arrival || this.date('today'),
      departure: query.departure || this.date('tomorrow'),
      roomtype: query.roomtype || ''
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
  componentWillMount() {
    var that = this;
    fetch('http://localhost:8080/hotels/'+this.props.params.hotelid)
    .then(function(response) {
      return response.json()
    }).then(function(json) {
      if (that.isMounted()) that.setState({hotel: json});
    }).catch(function(ex) { })
  },
  componentDidUpdate() {
    if (this.props.location.query.roomtype) {
      const price = this.refs.room.options[this.refs.room.selectedIndex].getAttribute('data-price')
      if (this.state.price !== price)
        this.setState({price: price});
    }
  },
  book: function(e) {
    var that = this;
    e.preventDefault();
    var body = new FormData(e.target);
    body.append('cookie', auth.getToken());
    fetch('http://localhost:8080/hotels/'+this.props.params.hotelid+'/bookings', {
      method: 'post',
      body: body
    })
    .then(function(response) {
      return response.json()
    }).then(function(json) {
      if (json.status === 400) {
        return that.history.replaceState({message: {
          type: 'error',
          value: 'Could not book the desired room'
        }}, that.props.location.pathname)
      }
      else {
        if (json.message.value === "You are not logged in.") {
          auth.logout();
          return that.history.replaceState({message: json.message}, '/login')
        }
        return that.history.replaceState({message: json.message}, that.props.location.pathname)
      }
    }).catch(function(ex) { })

  },
  updatePrice: function(e) {
    this.setState({
      roomtype: this.refs.room.options[this.refs.room.selectedIndex].value,
      price: this.refs.room.options[this.refs.room.selectedIndex].getAttribute('data-price')
    });
  },
  render: function() {
    const hotel = this.state.hotel;
    const manager = this.state.hotel.manager;
    const roomOpts = this.state.hotel.rooms.map(function (room) {
      return <option data-price={room.price} value={room.id}>{room.type}</option>
    });
    const room = this.state.hotel.rooms.map(function (room) {
      return (
        <tr>
          <td text="room.type.name">{room.type}</td>
          <td text="room.price+'€'">{room.price} €</td>
        </tr>
      );
    });
    return (
      <div>
        <Header/>
        <div className="hotel-cover" style={{backgroundImage: 'url(https://images.unsplash.com/photo-1447722939828-559fee94b1f5?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&s=bb08de9862897259abd5c17ffa75f085)'}}> </div>
        <div className="container row">
          <div className="seven columns">
            <div>
              <p className="clearmargin text-center isle-1-v">
                <span className={'star-'+hotel.rating}></span>
                <br/>
                <span className="h3 thin uppercase">
                  {hotel.name}
                </span>
              </p>
            </div>
            <h5 className="clearmargin thin">
              Category
            </h5>
            <p><span>{hotel.category}</span></p>
            <h5 className="clearmargin thin">
              Adress
            </h5>
            <p> <span>{hotel.address}</span></p>
            <h5 className="clearmargin thin">
              Photos
            </h5>
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <h4 className="thin">Manager </h4>
            <div className="media comment">
              <img src="http://www.sustainablebrands.com/sites/default/files/imagecache/100x100/imagecache/thumbnail_img/profilepics/picture-40780.jpg" alt="" className="avatar media__img"/>
              <div className="media__body">
                <h5><span>{manager.name}</span></h5>
                email: <span>{manager.email}</span>
              </div>
            </div>
          </div>
          <div className="five columns">
            <br/>
            <form action="'/hotels/'+hotel.id+'/bookings'" onSubmit={this.book} method="POST">
              <label>Check-in</label>
              <input ref="arrival" name="arrival" className="clear" defaultValue={this.state.arrival} type="date"/>
              <label>Check-out</label>
              <input ref="departure" name="departure" className="clear" defaultValue={this.state.departure} type="date"/>
              <label>Room type</label>
              <select ref="room" name="roomid" onChange={this.updatePrice} value={this.state.roomtype}>
                <option disabled>Room type</option>
                {roomOpts}
              </select>
              {this.state.price !== 0 ?
                <h4>Preço: <span id="price"></span>{this.state.price} €</h4>
                :
                <h4></h4>
                }
              {auth.loggedIn() ? (
                <button className="button button-primary" type="submit">Book</button>
                ) : (
                <Link className="button button-primary" to='/login'>Book</Link>
              )}
            </form>
          </div>
        </div>
        <div className="container">
          <hr/>
          <h3 className="thin">Rooms</h3>
          <table className="u-full-width">
            <thead>
              <tr>
                <th>Room Type</th>
                <th>Price</th>
              </tr>
            </thead>
            <tbody>
              {room}
            </tbody>
          </table>
        </div>
        <Footer/>
      </div>
    );
  }
});
