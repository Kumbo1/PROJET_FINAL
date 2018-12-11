using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;

namespace PROJET_FINAL.Controllers
{
    public class GoogleController : Controller
    {
        // GET: Google
        public ActionResult Index()
        {
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
            return View();         
        }

      
        [HttpGet]
        public ActionResult Sauver(string lat, string lng)
        {          
            using (var db = new ProjetDBEntities2())
            {
                var latitude = new SqlParameter("plat", lat);
                var longitude = new SqlParameter("plng", lng);
                db.Database.ExecuteSqlCommand("execute NomFonction @plat, @plng", latitude, longitude);
            }

                return View();
        }
            
        
       
    }
}