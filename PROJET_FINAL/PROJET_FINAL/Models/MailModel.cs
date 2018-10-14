using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using System.Web.Helpers;

namespace PROJET_FINAL.Models
{
    public class MailModel
    {

        [DataType(DataType.EmailAddress), Display(Name = "To")]
        [Required]
        public string ToEmail { get; set; }
        [Display(Name = "Body")]
        [DataType(DataType.MultilineText)]
        public string EMailBody { get; set; }
        [Display(Name = "Subject")]
        public string EmailSubject { get; set; }
        [DataType(DataType.EmailAddress)]
        [Display(Name = "CC")]
        public string EmailCC { get; set; }
        [DataType(DataType.EmailAddress)]
        [Display(Name = "BCC")]
        public string EmailBCC { get; set; }

        public void SendEmail(int idclient, int idlivreur)
        {
            try
            {
                ProjetDBEntities2 db = new ProjetDBEntities2();
                var clientEmail =  db.Clients.Where(x => x.IdClient == idclient).Select(x => x.Courriel);
                var livreurNom = db.Livreurs.Where(y => y.IdLivreur == idlivreur).Select(y => y.PrenomClient);

                //Configuring webMail class to send emails  
                //gmail smtp server  
                WebMail.SmtpServer = "smtp.gmail.com";
                //gmail port to send emails  
                WebMail.SmtpPort = 587;
                WebMail.SmtpUseDefaultCredentials = true;
                //sending emails with secure protocol  
                WebMail.EnableSsl = true;
                //EmailId used to send emails from application  
                WebMail.UserName = "ladlp420@gmail.com";
                WebMail.Password = "lesamisdelaposte";

                //Sender email address.  
                WebMail.From = "ladlp420@gmail.com";

                //Send email  
                WebMail.Send(to: this.ToEmail, subject: this.EmailSubject, body: this.EMailBody, cc: this.EmailCC, bcc: this.EmailBCC, isBodyHtml: true);
                
            }
            catch (Exception)
            {
                throw new HttpUnhandledException();
            }
        }
        public void InitCourriel(string email, string message)
        {
            ToEmail = email;
            EMailBody = message;
        }
    }
}