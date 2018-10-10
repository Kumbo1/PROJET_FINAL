using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace PROJET_FINAL.Controllers
{
    public class LivreurCourantController : Controller
    {
        // GET: LivreurCourant
        public ActionResult Index()
        {
            var user = (int)Session["livreurID"];
            ProjetDBEntities2 db = new ProjetDBEntities2();
            return View(db.Commandes.ToList().Where(x => x.idLivreur == user));
        }
    }
}