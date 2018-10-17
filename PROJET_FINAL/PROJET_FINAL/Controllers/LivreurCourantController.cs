using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.SqlClient;
using System.Data.Sql;

namespace PROJET_FINAL.Controllers
{
    public class LivreurCourantController : Controller
    {
        // GET: LivreurCourant
        public ActionResult Index()
        {
            if (Session.Count == 0)
            {
                return RedirectToAction("Index", "Login");
            }
            else
            {
                var user = (int)Session["livreurID"];
                ProjetDBEntities2 db = new ProjetDBEntities2();
                return View(db.Commandes.ToList().Where(x => x.idLivreur == user && x.EstFini == null));
            }

        }

        public ActionResult EstFini(int id)
        {
            if (id == null)
            {
                return HttpNotFound();
            }
            using (ProjetDBEntities2 db = new ProjetDBEntities2())
            {
                var idcommande = new SqlParameter("@pidcommande", id);
                db.Database.ExecuteSqlCommand("execute TerminerCommande @pidcommande", idcommande);
                return RedirectToAction("Index");
            }
        }
    }
}