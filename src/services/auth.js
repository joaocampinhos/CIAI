class Auth {

  login(form, cb) {
    cb = arguments[arguments.length - 1]
    if (localStorage.token) {
      if (cb) cb(true)
      this.onChange(true)
      return
    }
    log(form, (res) => {
      console.log(res);
      if(res.cookie) {
        localStorage.token = res.cookie.value;
        localStorage.user = JSON.stringify(res.user);
        if (cb) cb(true, res.message);
        this.onChange(true)
      }
      else {
        if (cb) cb(false, res.message);
        this.onChange(false)
      }
    });
  }

  getToken() {
    return localStorage.token;
  }

  getUser() {
    return JSON.parse(localStorage.user);
  }

  logout(cb) {
    delete localStorage.token
    delete localStorage.user
    if (cb) cb()
    this.onChange(false)
  }

  loggedIn() {
    return !!localStorage.token
  }

  onChange() {}
}

export default new Auth()

function log(form, cb) {
  setTimeout(() => {
    fetch('http://localhost:8080/login', {
      method: 'post',
      body: form
    })
    .then(function(response) {
      return response.json()
    }).then(function(json) {
      cb(json)
    }).catch(function(err) {})
  }, 0)
}