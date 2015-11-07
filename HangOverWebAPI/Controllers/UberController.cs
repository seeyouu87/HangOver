using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace HangOver.Controllers
{
    public class UberController : Controller
    {
        // GET: Uber
        public ActionResult Index()
        {
            return View();
        }

        public void Surge()
        {
            Response.Write("something surge");
        }
    }
}