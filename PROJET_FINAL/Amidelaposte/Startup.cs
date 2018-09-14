using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(Amidelaposte.Startup))]
namespace Amidelaposte
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
