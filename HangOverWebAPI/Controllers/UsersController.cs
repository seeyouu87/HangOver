using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using HangOver.Models;

namespace HangOver.Controllers
{
    public class UsersController : ApiController
    {
        private HoDbContext db = new HoDbContext();

        public class CustUser
        {
            public int id { get; set; }
            public String name { get; set; }
            public String gender { get; set; }
            public DateTime dob { get; set; }
            public String email { get; set; }
            public String phone { get; set; }
            public String ImgUrl { get; set; }
            public String address { get; set; }
            public String geocode { get; set; }
            public String emgcontact { get; set; }
            public String emgrelationship { get; set; }
            public String fbtoken { get; set; }
            public String ubertoken { get; set; }
            public String credit { get; set; }

        }
        // GET: api/Users
        public IQueryable<CustUser> GetUsers()
        {
            List<CustUser> entry = new List<CustUser>();
            foreach (User curUsers in db.Users)
            {
                CustUser custUser = new CustUser();
                custUser.address = curUsers.address;
                custUser.dob = curUsers.dob;
                custUser.email = curUsers.email;
                custUser.phone = curUsers.phone;
                custUser.geocode = curUsers.geocode;
                custUser.id = curUsers.id;
                custUser.name = curUsers.name;
                custUser.ubertoken = curUsers.ubertoken;
                custUser.ImgUrl = curUsers.ImgUrl;
                custUser.credit = curUsers.credit;
                entry.Add(custUser);
            }

            return entry.AsQueryable<CustUser>();
        }

        // GET: api/Users/5
        [ResponseType(typeof(User))]
        public IHttpActionResult GetUser(int id)
        {
            User user = db.Users.Find(id);
            if (user == null)
            {
                return NotFound();
            }
            List<Event> eventList = new List<Event>();
            foreach (Event myEvent in user.Events)
            {
                Event custEvent = new Event();
                custEvent.address = myEvent.address;
                
                custEvent.Users = new List<User>();
               
                custEvent.geocode = myEvent.geocode;
                custEvent.id = myEvent.id;
                custEvent.name = myEvent.name;

                eventList.Add(custEvent);

            }
            user.Events = eventList;
            return Ok(user);
        }

        // PUT: api/Users/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutUser(int id, User user)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != user.id)
            {
                return BadRequest();
            }

            db.Entry(user).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Users
        [ResponseType(typeof(User))]
        public IHttpActionResult PostUser(User user)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            
            if (db.Users.Where(o => o.email.Equals(user.email)).Count()>0)
            {
                User curUser = db.Users.Where(o => o.email.Equals(user.email)).First();
                User custUser = new Models.User();
                custUser.address = curUser.address;
                custUser.credit = curUser.credit;
                custUser.dob = curUser.dob;
                custUser.email = curUser.email;
                custUser.emgcontact = curUser.emgcontact;
                custUser.emgrelationship = curUser.emgrelationship;
                custUser.Events = new List<Event>();
                custUser.fbtoken = curUser.fbtoken;
                custUser.gender = curUser.gender;
                custUser.geocode = curUser.geocode;
                custUser.id = curUser.id;
                custUser.ImgUrl = curUser.ImgUrl;
                custUser.name = curUser.name;
                custUser.phone = curUser.phone;
                custUser.ubertoken = curUser.ubertoken;
                return Ok(custUser);
            }
            db.Users.Add(user);
            db.SaveChanges();
            user.Events = new List<Event>();
            return CreatedAtRoute("DefaultApi", new { id = user.id }, user);
        }

        // DELETE: api/Users/5
        [ResponseType(typeof(User))]
        public IHttpActionResult DeleteUser(int id)
        {
            User user = db.Users.Find(id);
            if (user == null)
            {
                return NotFound();
            }

            db.Users.Remove(user);
            db.SaveChanges();

            return Ok(user);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool UserExists(int id)
        {
            return db.Users.Count(e => e.id == id) > 0;
        }
    }
}