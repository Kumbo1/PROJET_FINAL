//------------------------------------------------------------------------------
// <auto-generated>
//     Ce code a été généré à partir d'un modèle.
//
//     Des modifications manuelles apportées à ce fichier peuvent conduire à un comportement inattendu de votre application.
//     Les modifications manuelles apportées à ce fichier sont remplacées si le code est régénéré.
// </auto-generated>
//------------------------------------------------------------------------------

namespace PROJET_FINAL
{
    using System;
    using System.Collections.Generic;
    
    public partial class Commandes
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Commandes()
        {
            this.Objets = new HashSet<Objets>();
        }
    
        public int IdCommande { get; set; }
        public int IdClient { get; set; }
        public Nullable<int> idLivreur { get; set; }
        public string InfosSup { get; set; }
        public System.DateTime Date { get; set; }
        public bool EstMajeur { get; set; }
        public string Adresse { get; set; }
        public Nullable<int> IdVille { get; set; }
        public string CodePostal { get; set; }
    
        public virtual Clients Clients { get; set; }
        public virtual Livreurs Livreurs { get; set; }
        public virtual Villes Villes { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Objets> Objets { get; set; }
    }
}