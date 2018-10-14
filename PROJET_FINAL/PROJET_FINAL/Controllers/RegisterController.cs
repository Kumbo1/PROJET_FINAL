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
    public class RegisterController : Controller
    {

        // GET: Register
        public ActionResult Index()
        {
            return View();
        }

        [HttpPost]
        public ActionResult RegisterClient(Register reg)
        {
            if (ModelState.IsValid)
            {
                using (ProjetDBEntities2 db = new ProjetDBEntities2())
                {
                    var queryUser = db.Clients.FirstOrDefault(u => u.Username == reg.Username);
                    if (queryUser == null)
                    {
                        var prenom = new SqlParameter("pprenom", reg.Prenom);
                        var nom = new SqlParameter("pnom", reg.Nom);
                        var ville = new SqlParameter("pville", reg.NomVille);
                        var username = new SqlParameter("pusername", reg.Username);
                        var adresse = new SqlParameter("padresse", reg.Adresse);
                        var codepostal = new SqlParameter("pcodepostal", reg.CodePostal);
                        var telephone = new SqlParameter("ptelephone", reg.Telephone);
                        var estmajeur = new SqlParameter("pestmajeur", reg.EstMajeur);
                        var estadmin = new SqlParameter("pestadmin", false);
                        var motdepasse = new SqlParameter("pmotdepasse", reg.MotDePasse);
                        var courriel = new SqlParameter("pcourriel", reg.Courriel);
                        db.Database.ExecuteSqlCommand("execute InsertClients @pprenom, @pnom, @pville" +
                            ", @pusername, @padresse, @pcodepostal, @ptelephone, @pestmajeur" +
                            ", @pestadmin, @pmotdepasse, @pcourriel", prenom, nom, ville, username, adresse, codepostal, telephone,
                            estmajeur, estadmin, motdepasse, courriel);
                        
                        return RedirectToAction("Index", "Welcome");
                    }
                    else
                    {
                        return RedirectToAction("Index", "Register");
                    }
                }

            }
            else
            {
                ModelState.AddModelError("", "Remplissez tous les espaces correctement");
                return RedirectToAction("Index", "Register");
            }



        }


        public ActionResult RegisterLivreur(Register reg)
        {
            if (ModelState.IsValid)
            {
                using (ProjetDBEntities2 db = new ProjetDBEntities2())
                {
                    var queryUser = db.Livreurs.FirstOrDefault(u => u.Username == reg.Username);
                    if (queryUser == null)
                    {
                        var prenom = new SqlParameter("pprenom", reg.Prenom);
                        var nom = new SqlParameter("pnom", reg.Nom);
                        var username = new SqlParameter("pusername", reg.Username);
                        var telephone = new SqlParameter("ptelephone", reg.Telephone);
                        var estmajeur = new SqlParameter("pestmajeur", reg.EstMajeur);
                        var motdepasse = new SqlParameter("pmotdepasse", reg.MotDePasse);
                        var courriel = new SqlParameter("pcourriel", reg.Courriel);
                        db.Database.ExecuteSqlCommand("execute InsertLivreurs @pprenom, @pnom" +
                            ", @pusername, @ptelephone, @pestmajeur" +
                            ", @pmotdepasse, @pcourriel", prenom, nom, username, telephone,
                            estmajeur, motdepasse, courriel);
                        return RedirectToAction("Index", "Welcome");
                    }
                    else
                    {
                        return RedirectToAction("Index", "Welcome");
                    }
                }

            }
            else
            {
                ModelState.AddModelError("", "Remplissez tous les espaces correctement");
                return RedirectToAction("Index", "Register");
            }
        }
    }
}
