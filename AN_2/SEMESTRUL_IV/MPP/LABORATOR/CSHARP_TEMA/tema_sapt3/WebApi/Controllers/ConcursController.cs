using Microsoft.AspNetCore.Mvc;
using Model;
using persistance;
using Server;
using services;
using System.Net.Mime;
using System.Runtime.CompilerServices;

namespace WebApi.Controllers
{
    [ApiController]
    [Route("concurs")]
    [Produces(MediaTypeNames.Application.Json)]
    public class ConcursController : ControllerBase
    {
        private readonly IServices _service;

        public ConcursController(DBContext context)
        {
            _service = new Service(context);
        }

        /// <summary>
        /// Add Proba request.
        /// </summary>
        /// <response code="200">Add Proba request.</response>
        [HttpPost]
        [Route("addProba")]
        [Consumes(MediaTypeNames.Application.Json)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        public Proba AddProba(Proba proba)
        {
             return _service.addProba(proba);
        }

        /// <summary>
        /// Delete Proba request.
        /// </summary>
        /// <response code="200">Delete Proba request.</response>
        [HttpPost]
        [Route("deleteProba")]
        [Consumes(MediaTypeNames.Application.Json)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        public Proba DeleteProba(Proba proba)
        {
            return _service.deleteProba(proba);
        }

        /// <summary>
        /// GetAll Proba request.
        /// </summary>
        /// <response code="200">GetAll Proba request.</response>
        [HttpPost]
        [Route("getAllProba")]
        [Consumes(MediaTypeNames.Application.Json)]
        [ProducesResponseType(StatusCodes.Status200OK)]
        public List<Proba> GetAllProba()
        {
            return _service.getAllProba();
        }

        /// <summary>
        /// FindById Proba request.
        /// </summary>
        /// <response code="200">FindById Proba request.</response>
        [HttpPost]
        [Route("findByIdProba")]
        [Consumes(MediaTypeNames.Application.Json)]
        [ProducesResponseType(typeof(Proba), StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public ActionResult<Proba> FindByIdProba(String id_proba)
        {
            Proba result = _service.findByIdProba(id_proba);
            if (result == null)
            {
                return NotFound(); // Returnează răspunsul HTTP cu codul 404 Not Found
            }

            return Ok(result); // Returnează răspunsul HTTP cu codul 200 OK și obiectul Proba
        }
    }
}
