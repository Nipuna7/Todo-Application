
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { motion } from 'framer-motion';
import AddTask from './AddTask';
import TaskList from './TaskList';

const API_BASE_URL = 'http://localhost:8080/task';

export default function Home() {
  const [tasks, setTasks] = useState([]);
  const [error, setError] = useState('');

  // Fetch latest 5 tasks on component mount
  useEffect(() => {
    fetchLatestTasks();
  }, []);

  const fetchLatestTasks = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/latest`);
      setTasks(response.data);
      setError('');
    } catch (err) {
      console.error('Error fetching tasks:', err);
      setError('Failed to fetch tasks');
    }
  };

  const handleAddTask = async (taskData) => {
    try {
      await axios.post(`${API_BASE_URL}/add`, taskData);
      await fetchLatestTasks();
      return { success: true };
    } catch (err) {
      return { 
        success: false, 
        error: err.response?.data || 'Error adding task' 
      };
    }
  };

  const handleMarkAsDone = async (id) => {
    try {
      await axios.put(`${API_BASE_URL}/done/${id}`);
      await fetchLatestTasks();
    } catch (err) {
      setError(err.response?.data || 'Error marking task as done');
    }
  };

  const handleDeleteTask = async (id) => {
    try {
      await axios.delete(`${API_BASE_URL}/delete/${id}`);
      await fetchLatestTasks();
    } catch (err) {
      setError(err.response?.data || 'Error deleting task');
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 p-8">
      <div className="max-w-6xl mx-auto">
        <motion.h1 
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          className="text-4xl font-bold text-gray-800 mb-8 text-center"
        >
          Todo Application
        </motion.h1>

        {error && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="mb-4 p-4 bg-red-100 text-red-700 rounded-lg text-center"
          >
            {error}
          </motion.div>
        )}

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          <AddTask onAddTask={handleAddTask} />
          <TaskList 
            tasks={tasks}
            onMarkAsDone={handleMarkAsDone}
            onDeleteTask={handleDeleteTask}
          />
        </div>
      </div>
    </div>
  );
}