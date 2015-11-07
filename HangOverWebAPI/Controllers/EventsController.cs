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
    public class EventsController : ApiController
    {
        private HoDbContext db = new HoDbContext();
        
        public class CustEvent
        {
            public int id { get; set; }
            public String name { get; set; }
            public String description { get; set; }
            public DateTime date { get; set; }
            public String address { get; set; }
            public String geocode { get; set; }
            public String eventphoto { get; set; }
            public String eventprice { get; set; }
            
        }

        // GET: api/Events
        public IQueryable<CustEvent> GetEvents()
        {
            List<CustEvent> entry =new List<CustEvent>();
            foreach (Event myevent in db.Events)
            {
                CustEvent custEvent = new CustEvent();
                custEvent.address = myevent.address;
                custEvent.date = myevent.date;
                custEvent.description = myevent.description;
                custEvent.eventphoto = myevent.eventphoto;
                custEvent.geocode = myevent.geocode;
                custEvent.id = myevent.id;
                custEvent.name = myevent.name;
                custEvent.eventprice = myevent.eventprice;
                entry.Add(custEvent);
            }

            return entry.AsQueryable<CustEvent>();
        }

        // GET: api/Events/5
        [ResponseType(typeof(Event))]
        public IHttpActionResult GetEvent(int id)
        {
            Event @event = db.Events.Find(id);
            if (@event == null)
            {
                return NotFound();
            }
            List<User> custUserList = new List<User>();
            foreach (User myUser in @event.Users)
            {
                User custUser = new User();
                custUser.address = myUser.address;
                custUser.credit = myUser.credit;
                custUser.dob = myUser.dob;
                custUser.email = myUser.email;
                custUser.emgcontact = myUser.emgcontact;
                custUser.emgrelationship = myUser.emgrelationship;
                custUser.Events = new List<Event>();
                custUser.fbtoken = myUser.fbtoken;
                custUser.gender = myUser.gender;
                custUser.geocode = myUser.geocode;
                custUser.id = myUser.id;
                custUser.ImgUrl = myUser.ImgUrl;
                custUser.name = myUser.name;
                custUser.phone = myUser.phone;
                custUser.ubertoken = myUser.ubertoken;
                custUserList.Add(custUser);

            }
            @event.Users = custUserList;
            return Ok(@event);
        }
        
        // PUT: api/Events/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutEvent(int id, Event @event)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != @event.id)
            {
                return BadRequest();
            }

            db.Entry(@event).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!EventExists(id))
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

        // POST: api/Events
        [ResponseType(typeof(Event))]
        public IHttpActionResult PostEvent(Event @event)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Events.Add(@event);
            db.SaveChanges();
            @event.Users = new List<User>();
            return CreatedAtRoute("DefaultApi", new { id = @event.id }, @event);
        }

        // DELETE: api/Events/5
        [ResponseType(typeof(Event))]
        public IHttpActionResult DeleteEvent(int id)
        {
            Event @event = db.Events.Find(id);
            if (@event == null)
            {
                return NotFound();
            }

            db.Events.Remove(@event);
            db.SaveChanges();

            return Ok(@event);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool EventExists(int id)
        {
            return db.Events.Count(e => e.id == id) > 0;
        }
    }
}