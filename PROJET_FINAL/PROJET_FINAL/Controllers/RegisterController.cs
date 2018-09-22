using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace PROJET_FINAL.Controllers
{
    public class RegisterController : Controller
    {
        
        // GET: Register
        public ActionResult Index()
        {
            return View();
        }

        [HttpPost]
        public ActionResult RegisterClient(PROJET_FINAL.Client client)
        {
            var reg = new Register();
            if (ModelState.IsValid)
            {
                using (ProjetDBEntities2 db = new ProjetDBEntities2())
                {
                    var queryUser = db.Clients.FirstOrDefault(u => u.Username == client.Username);
                    if (queryUser == null)
                    {
                        reg.Prenom = client.PrenomClient;
                        reg.Nom = client.NomClient;
                        reg.Username = client.Username;
                        reg.Adresse = client.Adresse;
                        reg.CodePostal = client.CodePostal;
                        reg.Telephone = client.Telephone;
                        reg.EstMajeur = client.EstMajeur;
                        reg.MotDePasse = client.MotDePasse;
                        reg.Courriel = client.Courriel;
                        db.Clients.Add(reg);
                        db.SaveChanges();
                        return RedirectToAction("Login");
                    }
                    else
                    {
                        return RedirectToAction("Register");
                    }
                                  
                }
            }
            
            return View();
        }
    }
}