using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.Sql;
using System.Data.SqlClient;

namespace PROJET_FINAL.Controllers
{
    public class ClientCommController : Controller
    {
        // GET: ClientComm
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult AjouterCommande(Commande comm)
        {

            if (ModelState.IsValid)
            {
                using (ProjetDBEntities2 db = new ProjetDBEntities2())
                {                  
                        var date = DateTime.Now.ToString("M/dd/yyyy");
                        var infosup = new SqlParameter("pinfosup", comm.InfosSup);
                        var estmajeur = new SqlParameter("pestmajeur", comm.EstMajeur);                        
                        var adresse = new SqlParameter("padresse", comm.Adresse);
                        var codepostal = new SqlParameter("pcodepostal", comm.CodePostal);                       
                        db.Database.ExecuteSqlCommand("execute AjouterCommande @pinfosup, @pdate, @pestmajeur" +
                            ", @padresse, @pcodepostal", infosup, date, estmajeur, adresse, codepostal);
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