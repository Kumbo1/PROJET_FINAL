using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PROJET_FINAL
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.Web.Mvc;

    public partial class Register
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Register()
        {
            this.Commandes = new HashSet<Commande>();
        }
        [Required]
        public string Prenom { get; set; }
        [Required]
        public string Nom { get; set; }
        public int IdRegister { get; set; }
        [Required]
        public string Username { get; set; }
        [Required]
        [DataType(DataType.PhoneNumber)]
        public string Telephone { get; set; }
        [Required]
        public bool EstMajeur { get; set; }
        [Required]
        [DataType(DataType.EmailAddress)]
        public string Courriel { get; set; }
        [Required]
        [DataType(DataType.Password)]
        public string MotDePasse { get; set; }
        [Required]
        [DataType(DataType.Password)]
        public string ConfirmMotDePasse { get; set; }
        [Required]
        public string Adresse { get; set; }
        [Required]
        [DataType(DataType.PostalCode)]
        public string CodePostal { get; set; }
        [Required]
        public string NomVille{ get; set; }

        public virtual Ville Ville { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Commande> Commandes { get; set; }
    }
}