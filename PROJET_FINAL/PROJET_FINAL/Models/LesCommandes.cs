using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PROJET_FINAL.Models
{
    public class LesCommandes
    {
        public int IdCommande { get; set; }
        public string InfoSup { get; set; }
        public DateTime DateCommande { get; set; }
        public string NomObjet { get; set; }
        public float Prix { get; set; }
    }
}