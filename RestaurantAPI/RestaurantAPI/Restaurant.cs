using System.ComponentModel.DataAnnotations;

namespace RestaurantAPI
{
    public class Restaurant
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Type { get; set; }
        public string Address { get; set; }
        public string Phone { get; set; }
        public string Description { get; set; }

        [Range(1, 5, ErrorMessage = "Value must be between 1 and 5.")]
        public int Stars { get; set; }

        public string PictureUrl { get; set; }

        public class Review
        {
            public string Author { get; set; }
            public string Body { get; set; }
            public int Rating { get; set; }
        }
    }
}


