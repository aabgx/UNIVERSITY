using Model;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using System.Threading.Tasks;

namespace ClientTest
{

    public class ConcursClient
    {
        private readonly HttpClient _httpClient;

        public ConcursClient(string baseAddress)
        {
            _httpClient = new HttpClient { BaseAddress = new Uri(baseAddress) };
            _httpClient.DefaultRequestHeaders.Accept.Clear();
            _httpClient.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
        }

        public async Task<Proba> AddProbaAsync(Proba proba)
        {
            HttpResponseMessage response = await _httpClient.PostAsJsonAsync("concurs/addProba", proba);
            response.EnsureSuccessStatusCode();
            return await response.Content.ReadFromJsonAsync<Proba>();
        }

        public async Task<Proba> DeleteProbaAsync(Proba proba)
        {
            HttpResponseMessage response = await _httpClient.PostAsJsonAsync("concurs/deleteProba", proba);
            response.EnsureSuccessStatusCode();
            return await response.Content.ReadFromJsonAsync<Proba>();
        }

        public async Task<List<Proba>> GetAllProbaAsync()
        {
            HttpResponseMessage response = await _httpClient.PostAsync("concurs/getAllProba", null);
            response.EnsureSuccessStatusCode();
            return await response.Content.ReadFromJsonAsync<List<Proba>>();
        }

        public async Task<Proba> FindByIdProbaAsync(String id_proba)
        {
            HttpResponseMessage response = await _httpClient.PostAsync("concurs/findByIdProba?id_proba="+id_proba, null);
            if (response.IsSuccessStatusCode)
            {
                return await response.Content.ReadFromJsonAsync<Proba>();
            }
            else if (response.StatusCode == System.Net.HttpStatusCode.NotFound)
            {
                return null;
            }
            else
            {
                throw new Exception("Failed to retrieve Proba by ID.");
            }
        }
    }
}
