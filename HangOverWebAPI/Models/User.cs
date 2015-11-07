using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HangOver.Models
{
    public class User
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int id { get; set; }
        public String name { get; set; }
        public String gender { get; set; }//user's gender
        public DateTime dob { get; set; }//date of birth, to ensure user over 18yrs old
        public String email { get; set; }//email 
        public String phone { get; set; }//user's phone number
        public String ImgUrl { get; set; }//user's profile image
        public String address { get; set; }//address for home
        public String geocode { get; set; }//geocode for home address
        public String emgcontact { get; set; }//emergencyy contact number
        public String emgrelationship { get; set; }//Emergency contact's relationship
        public String fbtoken { get; set; }//for linking up facebook account
        public String ubertoken { get; set; }//for linking up uber account
        public String credit { get; set; } //user's credit to be used for paying event price, also get reward credits while achieve certain mission

        public void Event(){
            this.Events = new HashSet<Event>();
        }
        public virtual ICollection<Event> Events { get; set; }
    }
}