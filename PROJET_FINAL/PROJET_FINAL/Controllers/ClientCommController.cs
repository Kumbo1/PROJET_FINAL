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
    public class ClientCommController : Controller
    {
        // GET: ClientComm
        public ActionResult Index()
        {
            return View();
        }
        [Authorize]
        [HttpPost]
        public ActionResult AjouterCommande(CommandeClient comm)
        {

            if (ModelState.IsValid)
            {
                var queryidclient = Session["userID"];
                var querydate = DateTime.Now.ToString("M/dd/yyyy");
                using (ProjetDBEntities2 db = new ProjetDBEntities2())
                {
                    var idclient = new SqlParameter("pidclient", queryidclient);
                    var infosup = new SqlParameter("pinfosup", comm.InfosSup);
                    var nomobjet = new SqlParameter("pnomobjet", comm.NomObjet);                    
                    var prixapprox = new SqlParameter("pprixapprox", comm.PrixApprox);
                    var categorie = new SqlParameter("pnomcategorie", comm.Categorie);
                    var estmajeur = new SqlParameter("pestmajeur", comm.EstMajeur);                        
                    var adresse = new SqlParameter("padresse", comm.Adresse);
                    var ville = new SqlParameter("pville", comm.NomVille);
                    var codepostal = new SqlParameter("pcodepostal", comm.CodePostal);
                    var date = new SqlParameter("pdatecomm", querydate);
                    db.Database.ExecuteSqlCommand("execute AjoutCommande @pidclient, @pinfosup, @pnomobjet, @pprixapprox, @pnomcategorie, @pestmajeur" +
                    ", @padresse, @pville, @pcodepostal, @pdatecomm", idclient, infosup, nomobjet, prixapprox, categorie, estmajeur, adresse, ville, codepostal, date);

                    return RedirectToAction("Index", "Welcome");                   
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