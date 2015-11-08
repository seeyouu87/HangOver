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

        //handle authentication code returned from Uber
        public void Code()
        {
            if (Request["code"] != null && !string.IsNullOrEmpty(Request["code"]))
            {
                Response.Write("<script type=\"text/javascript\">window.close();</script>");
            }
        }

        //handle surge page after users have confirmed the surge multiplier
        public void Surge()
        {
            Response.Write("something surge");
        }
    }
}