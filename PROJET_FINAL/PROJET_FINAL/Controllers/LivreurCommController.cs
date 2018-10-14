using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.SqlClient;
using System.Data.Sql;

namespace PROJET_FINAL.Controllers
{
    public class LivreurCommController : Controller
    {
        // GET: LivreurComm
        public ActionResult Index()
        {
            if (Session.Count == 0)
            {
                return RedirectToAction("Index", "Login");
            }
            else
            {
                ProjetDBEntities2 db = new ProjetDBEntities2();
                if (!(bool)Session["EstMajeur"])
                    return View(db.Commandes.ToList().Where(x => x.idLivreur == null && x.EstMajeur == (bool)Session["EstMajeur"]));
                else
                    return View(db.Commandes.ToList().Where(x => x.idLivreur == null));
            }


        }

        public ActionResult Assigner(int id)
        {

            if (id == null)
            {
                return HttpNotFound();
            }
            using (ProjetDBEntities2 db = new ProjetDBEntities2())
            {
                var querylivreur = Session["livreurID"];
                var idlivreur = new SqlParameter("@pidlivreur", querylivreur);
                var idcommande = new SqlParameter("@pidcommande", id);
                db.Database.ExecuteSqlCommand("execute AssocierLivreurCommande @pidcommande, @pidlivreur", idcommande, idlivreur);
                return RedirectToAction("Index");
            }
        }

    }
}