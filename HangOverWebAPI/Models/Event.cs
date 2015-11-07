using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HangOver.Models
{
    public class Event
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int id { get; set; }
        public String name { get; set; }
        public String description { get; set; }
        public DateTime date { get; set; }
        public String address { get; set; }
        public String geocode { get; set; }
        public String eventphoto { get; set; }
        public String eventprice { get; set; }
        public void User()
        {
            this.Users = new HashSet<User>();
        }
        public virtual ICollection<User> Users { get; set; }
    }
}

