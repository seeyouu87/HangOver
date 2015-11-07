using HangOver.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using Twilio;

namespace HangOver.Controllers
{
    public class GeneralController : Controller
    {

        private HoDbContext db = new HoDbContext();

        // GET: General
        public ActionResult Index()
        {
            return View();
        }

        public void Ubercode() {
            if (Request["code"] != null && !string.IsNullOrEmpty(Request["code"]))
            {
                Response.Write(Request["code"].ToString());
            }
        }
        //for user to register on event
        // GET: General/Register/5/1
        public void Register(int? eventid, int? userId)
        {
            Event @event = db.Events.Find(eventid);
            User @user = db.Users.Find(userId);
            if (@event == null)
            {
                Response.StatusCode = 404;
                Response.End();
            }
            if (@user == null)
            {
                Response.StatusCode = 404;
                Response.End();
            }
            @event.Users.Add(@user);
            db.Entry(@event).State = EntityState.Modified;
            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {

            }
            Response.StatusCode = 200;
        }

        public void SendHome() {
            //Find your Account Sid and Auth Token at twilio.com / user / account
            string AccountSid = "ACb2f1376756059a1e5519b0d067b82148"; //test code: "AC6c6ee3afd79c247db7007242708362bc";
            string AuthToken = "0d08e8a2856d360e9cd0816e19baa15f";//test token :"d32142ce915c5377daa34e7efd06183b";

            var twilio = new TwilioRestClient(AccountSid, AuthToken);
            var message = twilio.SendMessage("+14803606485 ", "+6596372198", "Dear Mr Tan, Mr Lee (no:98623241) is sending your son back home by taxi as he is too drunk.\n --By HangOver", "");

        }
        public void SendHospital()
        {
            string AccountSid = "ACb2f1376756059a1e5519b0d067b82148"; //test code: "AC6c6ee3afd79c247db7007242708362bc";
            string AuthToken = "0d08e8a2856d360e9cd0816e19baa15f";//test token :"d32142ce915c5377daa34e7efd06183b";

            var twilio = new TwilioRestClient(AccountSid, AuthToken);
            var message = twilio.SendMessage("+14803606485 ", "+6596372198", "Dear Mr Tan, Mr Lee (no:98623241) is sending your son to hospital by taxi as he is too drunk.\n --By HangOver", "");

        }
    }
}