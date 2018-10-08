using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.Sql;
using System.Data.SqlClient;
using PROJET_FINAL.Models;

namespace PROJET_FINAL.Controllers
{
    public class CommCourantController : Controller
    {
        // GET: CommCourant
        public ActionResult Index()
        {           
            var user = (int)Session["clientID"];
            ProjetDBEntities2 db = new ProjetDBEntities2();
            return View(db.Commandes.ToList().Where(x => x.IdClient == user));            
        }

        public ActionResult Supprimer()
        {
            return View();
        }
       
    }
}