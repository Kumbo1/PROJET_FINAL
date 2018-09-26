using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PROJET_FINAL.Models
{
    using System;
    using System.Collections.Generic;
    
    public partial class CommandeClient
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public CommandeClient()
        {
            this.Objets = new HashSet<Objet>();
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

        public virtual Client Client { get; set; }
        public virtual Livreur Livreur { get; set; }
        public virtual Ville Ville { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Objet> Objets { get; set; }
    }
}
