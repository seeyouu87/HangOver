namespace HangOver.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class hangover : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Events",
                c => new
                    {
                        id = c.Int(nullable: false, identity: true),
                        name = c.String(),
                        description = c.String(),
                        date = c.DateTime(nullable: false),
                        address = c.String(),
                        geocode = c.String(),
                        eventphoto = c.String(),
                    })
                .PrimaryKey(t => t.id);
            
            CreateTable(
                "dbo.Users",
                c => new
                    {
                        id = c.Int(nullable: false, identity: true),
                        name = c.String(),
                        gender = c.String(),
                        dob = c.DateTime(nullable: false),
                        email = c.String(),
                        phone = c.String(),
                        ImgUrl = c.String(),
                        address = c.String(),
                        geocode = c.String(),
                        emgcontact = c.String(),
                        emgrelationship = c.String(),
                        fbtoken = c.String(),
                        ubertoken = c.String(),
                        credit = c.String(),
                    })
                .PrimaryKey(t => t.id);
            
            CreateTable(
                "dbo.UserEvents",
                c => new
                    {
                        User_id = c.Int(nullable: false),
                        Event_id = c.Int(nullable: false),
                    })
                .PrimaryKey(t => new { t.User_id, t.Event_id })
                .ForeignKey("dbo.Users", t => t.User_id, cascadeDelete: true)
                .ForeignKey("dbo.Events", t => t.Event_id, cascadeDelete: true)
                .Index(t => t.User_id)
                .Index(t => t.Event_id);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.UserEvents", "Event_id", "dbo.Events");
            DropForeignKey("dbo.UserEvents", "User_id", "dbo.Users");
            DropIndex("dbo.UserEvents", new[] { "Event_id" });
            DropIndex("dbo.UserEvents", new[] { "User_id" });
            DropTable("dbo.UserEvents");
            DropTable("dbo.Users");
            DropTable("dbo.Events");
        }
    }
}
