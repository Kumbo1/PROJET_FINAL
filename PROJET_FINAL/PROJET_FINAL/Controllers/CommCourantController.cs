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
            if (Session.Count == 0)
            {
                return RedirectToAction("Index", "Login");
            }
            else
            {
                var user = (int)Session["clientID"];
                ProjetDBEntities2 db = new ProjetDBEntities2();
                return View(db.Commandes.ToList().Where(x => x.IdClient == user && x.EstFini == null));
            }

        }
        [HttpGet]
        public ActionResult Supprimer(int id)
        {
            if (id == null)
            {
                return HttpNotFound();
            }
            using (ProjetDBEntities2 db = new ProjetDBEntities2())
            {
                var idcommande = new SqlParameter("@pidcommande", id);
                db.Database.ExecuteSqlCommand("execute supprimercommande @pidcommande", idcommande);
                return RedirectToAction("Index");
            }


        }

    }
}