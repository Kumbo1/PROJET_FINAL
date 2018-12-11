using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.SqlClient;
using System.Data.Sql;
using System.Configuration;

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
                //Google
                string markers = "[";
                string conString = ConfigurationManager.ConnectionStrings["ProjetDBEntities"].ConnectionString;
                SqlCommand cmd = new SqlCommand("ShowMapWithData");
                if (conString.ToLower().StartsWith("metadata="))
                {
                    System.Data.Entity.Core.EntityClient.EntityConnectionStringBuilder efBuilder = new System.Data.Entity.Core.EntityClient.EntityConnectionStringBuilder(conString);
                    conString = efBuilder.ProviderConnectionString;
                }
                using (SqlConnection con = new SqlConnection(conString))
                {
                    cmd.Connection = con;
                    con.Open();
                    using (SqlDataReader sdr = cmd.ExecuteReader())
                    {
                        while (sdr.Read())
                        {
                            markers += "{";
                            markers += string.Format("'title': '{0}',", sdr["NomObjet"]);
                            markers += string.Format("'lat': '{0}',", sdr["latCommande"]);
                            markers += string.Format("'lng': '{0}',", sdr["longCommande"]);
                            markers += "},";
                        }
                    }
                    con.Close();
                }

                markers += "];";
                ViewBag.Markers = markers;
                //
                ProjetDBEntities2 db = new ProjetDBEntities2();
                //if (!(bool)Session["EstMajeur"])
                //    return View(db.Commandes.ToList().Where(x => x.idLivreur == null && x.EstMajeur == (bool)Session["EstMajeur"] && x.EstFini == null));
                //else
                    return View(db.Commandes.ToList().Where(x => x.idLivreur == null && x.EstFini == null));
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