using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.Sql;
using System.Data.SqlClient;
using PROJET_FINAL.Models;
using GoogleMaps.LocationServices;

namespace PROJET_FINAL.Controllers
{
    public class ClientCommController : Controller
    {
        // GET: ClientComm
        public ActionResult Index()
        {
            if(Session.Count == 0)
            {
                return RedirectToAction("Index", "Login");
            }
            else
                return View();
        }
        
        [HttpPost]
        public ActionResult AjouterCommande(CommandeClient comm)
        {
            //Google api
            
            var location = new GoogleLocationService("AIzaSyCS7pQ5bFOQhHENri9wbnCMN-lczbApfNU");
            var point = location.GetLatLongFromAddress(comm.Adresse + comm.CodePostal);
            var longitude = point.Longitude;
            var latitude = point.Latitude;            
            //
                var queryidclient = Session["clientID"];
                var querydate = DateTime.Now.ToString("MM/dd/yyyy hh:mm tt");
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
                //Google insert
                var lng = new SqlParameter("plng", longitude);
                var lat = new SqlParameter("plat", latitude);
                db.Database.ExecuteSqlCommand("execute InsertCommMap @plng, @plat", lng, lat);
                //


                    return RedirectToAction("Index", "Home");                   
                }



         
        }
    }
}