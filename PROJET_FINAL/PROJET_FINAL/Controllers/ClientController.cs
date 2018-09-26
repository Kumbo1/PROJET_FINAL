using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace PROJET_FINAL.Controllers
{
    public class ClientController : Controller
    {
        // GET: Client
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult AjouterCommande()
        {
            using (var db = new ProjetDBEntities2())
            {
                string info = Request.Form["infosup"];
                var date = DateTime.Now;
                string majeur = Request.Form["majeur"];
                string adresse = Request.Form["adresse"];
                string codepostal = Request.Form["codepostale"];

            }
                return View();
        }
    }
}