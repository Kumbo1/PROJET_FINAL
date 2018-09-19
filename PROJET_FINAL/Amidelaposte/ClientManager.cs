using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Amidelaposte
{
    public class ClientManager
    {
        public void InsertClient(string idclient, string prenomclient,string nomclient,string username, string adresse,string codepostal,string telephone)
        {
            using (var context = new ProjetDBEntities())
            {
                var clientEntity = context.Clients.FirstOrDefault(p => p.IdClient == idclient);
                if(clientEntity == null)
                {
                    var p = new Client();
                    p.PrenomClient = prenomclient;
                    p.NomClient = nomclient;
                    p.Username = username;
                    p.Adresse = adresse;
                    p.CodePostal = codepostal;
                    p.Telephone = telephone;
                    context.Clients.Add(p);
                    context.SaveChanges();

                }
            }
        }
    }
}