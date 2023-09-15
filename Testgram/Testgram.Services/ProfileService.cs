﻿using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Testgram.Core;
using Testgram.Core.Exceptions;
using Testgram.Core.IServices;
using Testgram.Core.Models;

namespace Testgram.Services
{
    public class ProfileService : IProfileService
    {
        private readonly IUnitOfWork _unitOfWork;

        public ProfileService(IUnitOfWork unitOfWork)
        {
            this._unitOfWork = unitOfWork;
        }

        public async Task<Profile> CreateProfile(Profile profile)
        {
            try
            {
                await _unitOfWork.Profile.AddAsync(profile);
                await _unitOfWork.CommitAsync();
                return profile;
            }
            catch (DBException e)
            {
                throw;
            }
            catch (Exception e)
            {
                throw new ArgumentException("Internal error.");
            }
        }

        public async Task DeleteProfile(Profile profile)
        {
            try
            {
                _unitOfWork.Profile.Remove(profile);
                await _unitOfWork.CommitAsync();
            }
            catch (DBException e)
            {
                throw;
            }
            catch (Exception e)
            {
                throw new ArgumentException("Internal error.");
            }
        }

        public async Task UpdateProfile(Profile profileToBeUpdated, Profile profile)
        {
            try
            {
                profileToBeUpdated.Biografy = profile.Biografy;
                profileToBeUpdated.Username = profile.Username;
                profileToBeUpdated.Email = profile.Email;
                profileToBeUpdated.FirstName = profile.FirstName;
                profileToBeUpdated.LastName = profile.LastName;
                await _unitOfWork.CommitAsync();
            }
            catch (DBException e)
            {
                throw;
            }
            catch (Exception e)
            {
                throw new ArgumentException("Internal error.");
            }
        }

        public async Task<IEnumerable<Profile>> GetAllProfiles()
        {
            return await _unitOfWork.Profile.GetAllProfilesAsync();
        }

        public async Task<Profile> GetProfileByEmail(string email)
        {
            return await _unitOfWork.Profile.GetProfileByEmailAsync(email);
        }

        public async Task<Profile> GetProfileById(long id)
        {
            return await _unitOfWork.Profile.GetProfileByIdAsync(id);
        }

        public async Task<Profile> GetProfileByUsername(string username)
        {
            return await _unitOfWork.Profile.GetProfileByUsernameAsync(username);
        }
    }
}