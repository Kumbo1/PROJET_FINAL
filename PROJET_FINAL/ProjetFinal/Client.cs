//------------------------------------------------------------------------------
// <auto-generated>
//     Ce code a été généré à partir d'un modèle.
//
//     Des modifications manuelles apportées à ce fichier peuvent conduire à un comportement inattendu de votre application.
//     Les modifications manuelles apportées à ce fichier sont remplacées si le code est régénéré.
// </auto-generated>
//------------------------------------------------------------------------------

namespace ProjetFinal
{
    using System;
    using System.Collections.Generic;
    
    public partial class Client
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Client()
        {
            this.Commandes = new HashSet<Commande>();
        }
    
        public string PrenomClient { get; set; }
        public string NomClient { get; set; }
        public int IdClient { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public string Adresse { get; set; }
        public string CodePostal { get; set; }
        public int IdVille { get; set; }
        public string Telephone { get; set; }
        public bool EstMajeur { get; set; }
        public bool EstAdmin { get; set; }
    
        public virtual Ville Ville { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Commande> Commandes { get; set; }
    }
}