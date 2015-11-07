namespace HangOver.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class hangover1 : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Events", "eventprice", c => c.String());
        }
        
        public override void Down()
        {
            DropColumn("dbo.Events", "eventprice");
        }
    }
}
