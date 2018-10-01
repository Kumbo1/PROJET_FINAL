using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace PROJET_FINAL.Controllers
{
    public class LoginController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }

        [HttpPost]
        public ActionResult LoginClient(PROJET_FINAL.Client client)
        {
            using (ProjetDBEntities2 db = new ProjetDBEntities2())
            {
                var userDetails = db.Clients.Where(x => x.Username == client.Username && x.MotDePasse == client.MotDePasse).FirstOrDefault();
                if (userDetails == null)
                {
                    client.LoginError = "Nom d'identifiant ou Mot de passe invalide.";
                    return View("Index", client);
                }
                else
                {
                    Session["userID"] = userDetails.IdClient;
                    return RedirectToAction("Index", "Welcome");
                }
            }
        }

        [HttpPost]
        public ActionResult LoginLivreur(PROJET_FINAL.Client livreur)
        {
            using (ProjetDBEntities2 db = new ProjetDBEntities2())
            {
                var userDetails = db.Livreurs.Where(x => x.Username == livreur.Username && x.MotDePasse == livreur.MotDePasse).FirstOrDefault();
                if(userDetails == null)
                {
                    livreur.LoginError = "Nom d'identifiant ou Mot de passe invalide.";
                    return View("Index", livreur);
                }
                else
                {
                    Session["EstLivreur"] = userDetails.IdLivreur;
                    Session["userID"] = userDetails.IdLivreur;
                    return RedirectToAction("Index", "Welcome");
                }
            }
        }
    }
}